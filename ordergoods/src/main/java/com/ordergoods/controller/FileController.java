package com.ordergoods.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ordergoods.common.CommonEnum;
import com.ordergoods.common.ResponseBean;
import com.ordergoods.common.ResultUtil;
import com.ordergoods.common.ToolsUtils;
import com.ordergoods.common.file.FileUtils;
import com.ordergoods.entity.SysFile;
import com.ordergoods.mapper.SysFileMapper;
import com.ordergoods.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by yan
 */
@Api(tags = {"文件管理"})
@Controller
@RequestMapping({"file","ueditor","/app/file"})//接口地址，访问这个url地址就可以访问下面的代码
public class FileController {

    private static  final Logger logger= LoggerFactory.getLogger(FileController.class);

    @Autowired//Controller层中需要用到的Service对象自动注入到Controller中
    private SysFileService fileService;

    @Value("${com.jane.file.baseFilePath}")
    private String baseFilePath;

    @Value("${com.jane.configjson.baseFilePath}")
    private String configJsonPath;

    @Autowired
    private SysFileMapper fileMapper;

    @RequestMapping("addPage")
    public String addPage(Model model){
        return "file/add";
    }

    @RequestMapping("add")
    @ResponseBody
    public ResponseBean addAssociation(SysFile sysFile, HttpServletRequest request, @RequestParam("file") MultipartFile[] files){
        logger.debug("sysFile:"+sysFile);
        if(sysFile==null){
            return ResultUtil.error(CommonEnum.BAD_REQUEST);
        }
        if(files==null||files.length<1){
            return ResultUtil.error("请上传文件！");
        }

        Long userId = ToolsUtils.getLoginUserId(request.getSession());
        String userName = ToolsUtils.getLoginUserName(request.getSession());
        sysFile.setObjectId(userId);
        sysFile.setField5(userName);
        for(MultipartFile file:files){
            SysFile saveOnly = fileService.saveOnly(sysFile,file);
            logger.debug("文件上传结果：{}",saveOnly);
        }
        return new ResponseBean<>(true, null, CommonEnum.SUCCESS_REQUEST);
    }


    /**
     * 跳转列表页面
     * @return
     */
    @RequestMapping("/listPage")
    public String listPage() {
        return "file/list";
    }




    @RequestMapping("deleteById")
    @ResponseBody
    public ResponseBean deleteById(@RequestParam Long id){
        logger.debug("deleteById:"+id);
        if(id==null||id<0){
            return  new ResponseBean(false,CommonEnum.BAD_REQUEST);
        }
        boolean delete = fileService.removeById(id);
        logger.debug("删除结果："+delete);
        return new ResponseBean<Map<String, Object>>(true, null, CommonEnum.SUCCESS_REQUEST);
    }

    @RequestMapping(value = "deleteBatchByIds",method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseBean deleteBatchByIds(@RequestParam List<Integer> idList){
        logger.debug("deleteBatchByIds:"+idList);
        if(idList==null||idList.isEmpty()){
            return  new ResponseBean(false,CommonEnum.BAD_REQUEST);
        }
        boolean delete = fileService.removeByIds(idList);
        logger.debug("批量删除结果："+delete);
        return new ResponseBean<Map<String, Object>>(true, null, CommonEnum.SUCCESS_REQUEST);
    }

    /**
     * 分页列表查询
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param queryParam 查询参数
     * @return
     */
    @RequestMapping("/list/{pageNum}/{pageSize}")
    @ResponseBody
    public ResponseBean getList(@PathVariable Integer pageNum, @PathVariable Integer pageSize, SysFile queryParam) {
        logger.debug("查询列表参数："+queryParam+",pageNum:"+pageNum+",pageSize:"+pageSize);
        IPage<SysFile> indexPage = new Page<>(pageNum, pageSize);
        Map<String, Object> objectMap = ToolsUtils.convertObjToMap(queryParam);
        QueryWrapper<SysFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.allEq(objectMap,false);
        IPage<SysFile> page = fileService.page(indexPage,queryWrapper);
        logger.debug("获取的列表："+page);
        //获取分页信息
        Map<String, Object> pageInfo = new HashMap<String, Object>();
        pageInfo.put("pageSize", page.getSize());
        pageInfo.put("pageNum", page.getCurrent());
        pageInfo.put("pages", page.getPages());
        pageInfo.put("total", page.getTotal());
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("fileList", page.getRecords());
        resultMap.put("pageInfo", pageInfo);
        return new ResponseBean<Map<String, Object>>(true, resultMap, CommonEnum.SUCCESS_REQUEST);
    }






    /**
     * 文件上传后删除所属对象的原先文件
     * @param sysFile
     * @param file
     * @return
     */
    @ApiOperation(value="文件上传后删除所属对象的原先文件")
    @RequestMapping(value = "/uploadAndDel",method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean fileUpload(SysFile sysFile, MultipartFile file){
        if(sysFile==null||file==null||file.isEmpty()){
            logger.debug("文件上传参数不对！");
            return new ResponseBean(false, CommonEnum.BAD_REQUEST);
        }
        SysFile uploadFile = fileService.saveOrUpdateFile(sysFile, file);

        if(uploadFile==null){
            return new ResponseBean(false,CommonEnum.BAD_REQUEST);
        }
        return  ResultUtil.successData(uploadFile);

    }

    /**
     * 只上传文件，返回保存的文件对象，并不会对原先文件进行删除
     * @param sysFile
     * @param file
     * @return
     */
    @ApiOperation(value="文件上传并返回保存的文件对象")
    @RequestMapping(value = "/uploadOnly",method = {RequestMethod.POST})
    @ResponseBody
    public ResponseBean fileUploadOnly(SysFile sysFile, @RequestParam(value = "file",required = true) MultipartFile file){
        if(sysFile==null||file==null||file.isEmpty()){
            logger.debug("文件上传参数不对！");
            return ResultUtil.error(CommonEnum.BAD_PARAM);
        }
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String extendName = "."+ FileUtils.getExtension(file);
        logger.debug("file.getResource:"+file.getResource());
        String saveFileName= UUID.randomUUID().toString().replaceAll("-","")+extendName;
        File saveFile=new File(baseFilePath+File.separator+saveFileName);
        if(!saveFile.getParentFile().exists()){
            saveFile.getParentFile().mkdirs();
        }
        try {
            //保存文件到最终路径
            file.transferTo(saveFile);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        sysFile.setFileName(originalFilename);
        sysFile.setFileType(file.getContentType());
        sysFile.setFileUrl(baseFilePath+File.separator+saveFileName);
        sysFile.setFileSize(size+"");
        sysFile.setSaveName(saveFileName);
        int insert = fileMapper.insert(sysFile);
        sysFile.setField1("/file/fileDown?saveName="+saveFileName);

        if(sysFile==null){
            return ResultUtil.error(CommonEnum.BAD_REQUEST);
        }
        return ResultUtil.successData(sysFile);

    }



    /**
     *
     * @param fileName--下载文件名
     * @param saveName--文件实际保存的名字
     * @return
     */
    @ApiOperation(value = "文件下载/回显",notes = "统一文件下载/回显接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "saveName",value = "文件名字",required = true),
            @ApiImplicitParam(name = "fileName",value = "下载展示文件名字")
    })
    @RequestMapping(value = "/fileDown",method =  {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<byte[]> fileDown(String saveName, String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        logger.debug("saveName:"+saveName);
        byte [] body = null;
        String fileUrl=baseFilePath+ File.separator+saveName;
        try {
            //获取到文件流
            InputStream in = new FileSystemResource(fileUrl).getInputStream();
            body = new byte[in.available()];
            in.read(body);
        } catch (IOException e1) {
            logger.debug("文件读入出错，文件路径为："+fileUrl);
            e1.printStackTrace();
        }
        if(StringUtils.isEmpty(fileName)){
            fileName=saveName;
        }
        //添加响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename="+ FileUtils.setFileDownloadHeader(request, fileName));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);//将文件内容以字节数组的形式返回给客户端
        return response;
    }

    /**
     * 第二种文件下载/回显方法
     * @param id tb_sys_file.id
     * @param fileName  实际下载的文件名字
     * @return
     */
    @ApiOperation(value = "根据ID文件下载/回显",notes = "path里面传文件ID")
    @RequestMapping(value = "/fileDown/{id}",method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<byte[]> fileDown(@PathVariable Long id, String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        SysFile sysFile = fileService.getById(id);
        byte [] body = null;
        String fileUrl=sysFile.getFileUrl();
        try {
            //获取到文件流
            InputStream in = new FileSystemResource(fileUrl).getInputStream();
            body = new byte[in.available()];
            in.read(body);
        } catch (IOException e1) {
            logger.debug("文件读入出错，文件路径为："+fileUrl);
            e1.printStackTrace();
        }
        //添加响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename="+ FileUtils.setFileDownloadHeader(request, fileName));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }

    /**
     * 通用从classpath根据模板文件名下载模板文件
     * @param fileName
     * @param request
     * @return
     */
    @RequestMapping(value = "/export")
    public ResponseEntity<byte[]> fileExport(String fileName, HttpServletRequest request){
        byte [] body = null;
        InputStream in=null;
        try {
            //获取到文件流
            in = FileController.class.getClassLoader().getResourceAsStream(fileName);
            if(in.available()<1){
                logger.info("没有从classpath下获取到导出的模板文件，将从configJsonPath下重试：{}",configJsonPath);
                in=new FileInputStream(new File(configJsonPath+File.separator+fileName));
            }
            logger.debug("需要导出的模板：{},获取的流为：{}",fileName,in);
            body = new byte[in.available()];
            logger.debug("导出模板大小：{},读入body大小:{}",in.available(),body.length);
            in.read(body);
        //添加响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename="+FileUtils.setFileDownloadHeader(request, fileName));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return null;
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }
}
