package citic.gack.sso.base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import citic.gack.sso.base.exception.SysException;
import citic.gack.sso.utils.Constants;

@SuppressWarnings("serial")
public abstract class BaseController<T> {

	protected static final String MESSAGE_CREATE = "添加成功";
	protected static final String MESSAGE_UPDATE = "修改成功";
	protected static final String MESSAGE_DESTROY = "删除成功";
	protected static final String MESSAGE_APPLY = "申请成功";
	protected static final String MESSAGE_APPLY_FAIL = "申请失败";
	protected static final String MESSAGE_OPINTION = "操作成功";
	protected static final String MESSAGE_OPINTION_FAIL = "操作失败,可能有人在你操作前进行了相关操作";

	protected static final String MESSAGE_CREATE_FAIL = "添加失败";
	protected static final String MESSAGE_UPDATE_FAIL = "修改失败,可能有人在你操作前进行了修改等操作";
	protected static final String MESSAGE_UPDATE_FAIL1 = "修改失败";
	protected static final String MESSAGE_DESTROY_FAIL = "删除失败";

	protected static final String MESSAGE_PAGE_ERROR = "获取分页信息失败";
	protected static final String MESSAGE_CHANGE_STAT = "请更改方案状态";
	protected static final String MESSAGE_EXIST_CONDITION = "请先删除该规则下的条件";

	protected static final String PAGE_INDEX = "index"; // 首页
	protected static final String PAGE_EDIT = "edit"; // 编辑界面
	protected static final String PAGE_SHOW = "show"; // 查看界面
	protected static final String PAGE_EDITNEW = "editNew";// 新增界面

	protected static final String SYS_THEME = Constants.valueOf("APPLICATION.THEME");// 主题

	private static final String UPLOAD_FILE_PATH = "/upload";
	private static final String UPLOAD_FILE_INPUTNAME = "upfile";

	/**
	 * 获取映射路径
	 * 
	 * @param businessPath
	 *            业务路径
	 * @param page
	 *            页面
	 * @return
	 */
	protected String getMappingUrl(String businessPath, String page) {
		return new StringBuilder().append(businessPath).append("/").append(page).toString();
	}

	/**
	 * 获取分页信息
	 * 
	 * @return
	 */
	protected PageInfo getPageInfo(HttpServletRequest request) throws SysException {
		PageInfo pageInfo = new PageInfo();
		try {
			if (StringUtils.isNotBlank(request.getParameter("page"))) {
				pageInfo.setPage(Integer.parseInt(request.getParameter("page")));
			}
			if (StringUtils.isNotBlank(request.getParameter("rp"))) {
				pageInfo.setRp(Integer.parseInt(request.getParameter("rp")));
			}
			pageInfo.setSortName(request.getParameter("sortname"));
			pageInfo.setSortOrder(request.getParameter("sortorder"));
		} catch (Exception e) {
			throw new SysException(MESSAGE_PAGE_ERROR, e);
		}
		return pageInfo;
	}

	/**
	 * 规则按优先级进行升序排序
	 * 
	 * @return
	 */
	protected PageInfo getSortPageInfo4Rule(PageInfo pageInfo) throws SysException {
		pageInfo.setSortName("level");
		pageInfo.setSortOrder("asc");
		return pageInfo;
	}

	/**
	 * @param absFilePath
	 *            String 文件读取绝对路径
	 * @param chineseFileName
	 *            String 下载时的文件中文命
	 * @param fileExt
	 *            String 文件扩展属性
	 * @param response
	 *            HttpServletResponse servlet Response对象
	 * @throws Exception
	 */
	protected final void httpDownLoad(String absFilePath, String chineseFileName, String fileExt,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		chineseFileName = new String(chineseFileName.getBytes("UTF-8"), "ISO8859-1");
		String downloadFileName = chineseFileName + "_" + format.format(new Date()) + "." + fileExt;
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
		ServletOutputStream outp = null;
		FileInputStream in = null;

		try {
			outp = response.getOutputStream();
			in = new FileInputStream(absFilePath);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				outp.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = UPLOAD_FILE_PATH)
	@ResponseBody
	public HashMap<String, String> uploadFile(
			@RequestParam(value = UPLOAD_FILE_INPUTNAME, required = false) MultipartFile file,
			HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = file.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uniqueFileName = UUIDGenerator.getUUID() + "." + ext;
		File targetFile = new File(path, uniqueFileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.processUpFile(targetFile.getAbsolutePath(), request);
	}

	/**
	 * 子类需要覆写文件上传处理过程
	 * 
	 * @param filePath
	 * @param request
	 * @return
	 */
	protected HashMap<String, String> processUpFile(String filePath, HttpServletRequest request) {
		return null;
	}

	@ExceptionHandler(Exception.class) // 在Controller类中添加该注解方法即可(注意：添加到某个controller，只针对该controller起作用)
	public void exceptionHandler(Exception ex, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		ex.printStackTrace();
		if (ex.getClass() == NoSuchRequestHandlingMethodException.class) {
			response.sendRedirect(request.getContextPath() + "/common/view/404.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/common/view/500.jsp");
		}
	}

	/**
	 * <br>
	 * 描 述：file转byte[] <br>
	 * 作 者：zhujianping <br>
	 * 历 史: (版本) 作者 时间 注释
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	protected byte[] file2Byte(MultipartFile file) throws SysException {
		byte[] in2b = null;
		try {
			InputStream inputStream = file.getInputStream();
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = inputStream.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			in2b = swapStream.toByteArray();
		} catch (Exception e) {
			throw new SysException(MESSAGE_PAGE_ERROR, e);
		}
		return in2b;
	}
}
