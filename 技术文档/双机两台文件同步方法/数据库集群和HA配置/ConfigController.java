package com.lsblj.web;

import com.lsblj.domain.Config;
import com.lsblj.service.ConfigService;
import com.lsblj.service.OperatorLogService;
import com.lsblj.utils.BljConstant;
import com.lsblj.utils.Operator_log;
import com.lsblj.utils.SystemCommandUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/config")
public class ConfigController {
	@Autowired
	private OperatorLogService operatorLogService;
	@Autowired
	private ConfigService configService;

	@RequestMapping("/listConfig")
	@ResponseBody
	public JSONObject listConfig(Config config,HttpServletRequest request, HttpSession session) {
		int page_start = Integer.parseInt(request.getParameter("start"));
		int page_length = Integer.parseInt(request.getParameter("length"));
		ArrayList<Object> resultConfigs = new ArrayList<Object>();
		ArrayList<Config> configs = new ArrayList<Config>();
		long total = 0;
		resultConfigs = (ArrayList<Object>)configService.findAll(config, page_start, page_length);
		if(CollectionUtils.isNotEmpty(resultConfigs)) {
			configs = (ArrayList<Config>)resultConfigs.get(0);
			total = ((ArrayList<Long>) resultConfigs.get(1)).get(0);
		}
		JSONArray jsonArray = JSONArray.fromObject(configs);
		JSONObject result = new JSONObject();
		result.put(BljConstant.SUCCESS, true);
		result.put("recordsTotal", total);
		result.put("recordsFiltered", total);
		result.put("data", jsonArray);
		return result;
	}

	@RequestMapping("/addConfig")
	@ResponseBody
	public JSONObject addConfig(Config config) {
		JSONObject result = new JSONObject();
		result.put(BljConstant.SUCCESS, true);
		if(result.getBoolean("success")) {
			Boolean r = configService.addConfig(config);
			result.put(BljConstant.SUCCESS, r);
		}
		return result;
	}

	@RequestMapping("/editConfig")
	@ResponseBody
	public JSONObject editConfig(Config config) {
		JSONObject result = new JSONObject();
		result.put(BljConstant.SUCCESS, true);
		if(result.getBoolean("success")) {
			Boolean r = configService.editConfig(config);
			result.put(BljConstant.SUCCESS, r);
		}
		return result;
	}

	@RequestMapping("/editConfig2")
	@ResponseBody
	public JSONObject editConfig2(Config config) {
		JSONObject result = new JSONObject();
		Boolean r = configService.editConfig2(config);
		result.put(BljConstant.SUCCESS, r);
		return result;
	}
	@RequestMapping("/userValue")
	@ResponseBody
	public JSONObject userValue(HttpServletRequest request, HttpSession session) {
		JSONObject result = new JSONObject();
		Config userValueConfig = configService.getByName("UserNetDiskSpace");
		String value = userValueConfig.getValue();
		result.put(BljConstant.SUCCESS, true);
		result.put("value",value);
		return result;
	}

	@RequestMapping("/delConfig")
	@ResponseBody
	public JSONObject delConfig(@RequestParam(value = "ids[]") Integer[] ids) {
		JSONObject result = new JSONObject();
		List<Integer> _ids =  Arrays.asList(ids);
		result.put(BljConstant.SUCCESS, true);
		if(_ids.isEmpty()) {
			result.put(BljConstant.SUCCESS, false);
			result.put("msg", "id不能为空");
		}
		if(result.getBoolean("success")) {
			Boolean r = configService.delConfig(_ids);
			result.put(BljConstant.SUCCESS, r);
		}
		return result;
	}


	@RequestMapping("/keepalived")
	@ResponseBody
	public JSONObject keepalived(HttpServletRequest request, HttpSession session) {
		JSONObject result = new JSONObject();
		result.put(BljConstant.SUCCESS, false);
		result.put("master", 0);
		result.put("running", 0);
		Config keepaliveConfig = configService.getByName("keepAliveConfigFile");
		try {
			FileInputStream inputStream = new FileInputStream(keepaliveConfig.getValue());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String str = null;
			Boolean found_virtual_address = false;
			while((str = bufferedReader.readLine()) != null)
			{
				str = str.trim();
				if(found_virtual_address) {
					result.put("virtual_ipaddress", str.trim());
					found_virtual_address = false;
				}else if(str.toLowerCase().contains("state master")) {
					str = str.trim();
					if("master".equals(str.substring(str.lastIndexOf(' ')+1).toLowerCase())) {
						result.put("master", 1);
					}
				}/*else if(str.toLowerCase().contains("auth_pass")) {
					result.put("mariadbkey", str.substring(str.indexOf("auth_pass")+9).trim());
				}*/else if(str.toLowerCase().contains("interface")) {
					result.put("interface", str.substring(str.lastIndexOf(' ')+1));
				}/*else if(str.toLowerCase().contains("mcast_src_ip")) {
					result.put("mcast_src_ip", str.substring(str.lastIndexOf(' ')+1));
				}*/else if(str.toLowerCase().contains("virtual_ipaddress")) {
					found_virtual_address=true;
				}
			}
			String master_ip = configService.getMasterIp();
			result.put("mcast_src_ip", master_ip);
			String[] resultString = SystemCommandUtil.execCmd("systemctl status keepalived").split("\n"); 
			for (String string : resultString) {
				if(string.toLowerCase().indexOf("active")>=0&&string.toLowerCase().indexOf("running")>=0) {
					result.put("running", 1);
					break;
				}
			}
/*			Config interfacePath = configService.getByName("interfacePath");
			FileInputStream inputStream2 = new FileInputStream(interfacePath.getValue()+"/ifcfg-"+result.getString("interface"));//
			BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
			str = null;
			while((str = bufferedReader2.readLine()) != null)
			{
				if(str.toLowerCase().indexOf("ipaddr")>=0) {
					result.put("mcast_src_ip", str.substring(str.lastIndexOf('=')+1));
					break;
				}
			}*/
			if(!result.isEmpty()) {
				result.put(BljConstant.SUCCESS, true);
			}
			//close
			inputStream.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/configKpalive")
	@ResponseBody
	public JSONObject configKpalive(HttpServletRequest request, HttpSession session) {
		JSONObject config = new JSONObject();
		if(request.getParameter("master")!=null) {
			config.put("master",request.getParameter("master"));
		}
/*		if(request.getParameter("key")!=null) {
			config.put("key",request.getParameter("key"));
		}*/
		if(request.getParameter("virtual_ipaddress")!=null) {
			config.put("virtual_ipaddress",request.getParameter("virtual_ipaddress"));
		}
		if(request.getParameter("mcast_src_ip")!=null) {
			configService.editMasterIp(request.getParameter("mcast_src_ip"));
			config.put("mcast_src_ip",request.getParameter("mcast_src_ip"));
		}
		if(request.getParameter("interface")!=null) {
			config.put("interface",request.getParameter("interface"));
		}
		if("0".equals(request.getParameter("isrun"))) {
			SystemCommandUtil.execCmd("systemctl stop keepalived");
			//关闭主从复制
			SystemCommandUtil.execCmd("mysql -e \"stop slave\"");
			JSONObject jsonObject = new JSONObject();
			//关闭文件同步
			SystemCommandUtil.execCmd("mv /opt/lsblj/server/bin/rsync1.sh /opt/lsblj/server/bin/rsync3.sh ");
			SystemCommandUtil.execCmd("mv /opt/lsblj/server/bin/rsync2.sh /opt/lsblj/server/bin/rsync4.sh ");
			jsonObject.put("success", true);
			return jsonObject;
		}
		SystemCommandUtil.execCmd("mv /opt/lsblj/server/bin/rsync3.sh /opt/lsblj/server/bin/rsync1.sh ");
		SystemCommandUtil.execCmd("mv /opt/lsblj/server/bin/rsync4.sh /opt/lsblj/server/bin/rsync2.sh ");
		Config keepaliveConfig = configService.getByName("keepAliveConfigFile");
		Config interfacePath = configService.getByName("interfacePath");
		String localIpString = "";
		//配置keepalived
		try {
			FileInputStream inputStream = new FileInputStream(interfacePath.getValue()+"/ifcfg-"+config.getString("interface"));
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String str = null;
			//获取ip地址
			while((str = bufferedReader.readLine()) != null)
			{
				if(str.toLowerCase().indexOf("ipaddr")>=0) {
					localIpString = str.substring(str.lastIndexOf('=')+1);
					break;
				}
			}
			//获取keepalive的配置文件
			inputStream = new FileInputStream(keepaliveConfig.getValue());
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			boolean found_virtual_address = false;
			StringBuilder kconentString = new StringBuilder();
			while((str = bufferedReader.readLine()) != null)
			{
				if(found_virtual_address) {
					kconentString.append("\t").append(config.getString("virtual_ipaddress")).append("\n");
					found_virtual_address = false;
				}else if(str.toLowerCase().contains("state")) {
					if(config.getInt("master")==1) {
						kconentString.append("\tstate MASTER\n");
					}else {
						kconentString.append("\tstate BACKUP\n");
					}
				}else if(str.toLowerCase().contains("priority")) {
					if(config.getInt("master")==1) {
						kconentString.append("\tpriority 100\n");
					}else {
						kconentString.append("\tpriority 90\n");
					}
				}else if(str.toLowerCase().contains("interface")) {
					kconentString.append("\tinterface ").append(config.getString("interface")).append("\n");
				}else if(str.toLowerCase().contains("mcast_src_ip")) {
					kconentString.append("\tmcast_src_ip ").append(localIpString).append("\n");
				}else if(str.toLowerCase().contains("virtual_server")) {
					kconentString.append("virtual_server ").append(config.getString("virtual_ipaddress")).append(" 443 {\n");
				}else if(str.toLowerCase().contains("real_server")) {
					kconentString.append("\treal_server ").append(localIpString).append(" 443 {\n");
				}else if(str.toLowerCase().contains("virtual_ipaddress")) {
					found_virtual_address=true;
					kconentString.append(str).append("\n");
				}else {
					kconentString.append(str).append("\n");
				}
			}
			inputStream.close();
			bufferedReader.close();
			File file =new File(keepaliveConfig.getValue());
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write(kconentString.toString());
			bw.close();
			SystemCommandUtil.execCmd("systemctl restart keepalived");
			Operator_log.addLog("系统配置","编辑","编辑HA集群配置["+request.getParameter("master")+"]","成功",operatorLogService);
		} catch (IOException e) {
			Operator_log.addLog("系统配置","编辑","编辑HA集群配置["+request.getParameter("master")+"]","失败",operatorLogService);
			e.printStackTrace();
		}
		//配置主从复制
		SystemCommandUtil.execCmd("/usr/bin/python3 /opt/lsblj/server/bin/MariadbReplication.pyc"+" "+request.getParameter("isrun")+" "+request.getParameter("master")+" "+ request.getParameter("mcast_src_ip")+" "+localIpString);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		return jsonObject;
	}
}
