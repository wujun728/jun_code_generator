<%if (t.isProtocol == 1) {%>
        ${g.copyright!}
<%}%>
package ${g.codePackage}.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ${g.codePackage}.controller.util.PermissionUtil;
import ${g.codePackage}.model.${g.entityName}Model;
import ${g.codePackage}.service.I${g.entityName}Service;
import com.postaop.oplog.bumng.annontion.OpLog;
import com.postaop.oplog.bumng.model.OpLogLevelEnum;
import com.postaop.oplog.bumng.model.OpLogTypeEnum;

import cn.linkea.bumng.core.security.util.SpringSecurityUtil;
import cn.linkea.common.util.PageList;
/**
 * ${g.name}控制器
 *
 * @author ${g.author}
 * @Date ${g.date}
 */
@Controller
@RequestMapping("/${strutil.toLowerCase(g.entityName)}")
public class ${g.entityName}Controller {

    private final static Logger logger = Logger.getLogger(${g.entityName}Controller.class);
    public final static String ${strutil.toUpperCase(g.entityName)}_MANAGE_VIEW = "/WEB-INF/${strutil.toLowerCase(g.entityName)}/${strutil.toLowerCase(g.entityName)}_manage.jsp";
    public final static String ${strutil.toUpperCase(g.entityName)}_DEFAULT_VIEW = "/WEB-INF/${strutil.toLowerCase(g.entityName)}/${strutil.toLowerCase(g.entityName)}_default.jsp";
    public final static String ${strutil.toUpperCase(g.entityName)}_ADD_UPDATE_VIEW = "/WEB-INF/${strutil.toLowerCase(g.entityName)}/${strutil.toLowerCase(g.entityName)}_add_update.jsp";
    public final static String ${strutil.toUpperCase(g.entityName)}_UPLOAD_VIEW = "/WEB-INF/${strutil.toLowerCase(g.entityName)}/${strutil.toLowerCase(g.entityName)}_upload.jsp";
    public final static int PAGE_SIZE = 10;

    @Autowired
    private I${g.entityName}Service ${g.lowerEntityName}Service;

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request,${g.entityName}Model model,Int page,ModelMap map) {
        String key = "${strutil.toUpperCase(g.entityName)}_QUERY";
        if (!PermissionUtil.hasPermission(key)) {
            return PermissionUtil.redirectNoPermission(key, map);
        }
        PageList<${g.entityName}Entity> list = ${g.lowerEntityName}Service.query(model, page,PAGE_SIZE);
        map.put("list", list);
        map.put("paginator", list.getPaginator());
        map.put("model", model);
        return ${strutil.toUpperCase(g.entityName)}_MANAGE_VIEW;
    }

    /**
     * 银行卡风控信息基础设置保存方法
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/saveDefault.do")
    @ResponseBody
    @OpLog(content = "银行卡风控默认卡更新", allParams = false, level = OpLogLevelEnum.IMPORT, type = OpLogTypeEnum.UPDATE)
    public Map<String, String> save(HttpServletRequest request,
                                    @RequestParam(value = "cardNo", required = false) String cardNo,
                                    @RequestParam(value = "cardType", required = false) String cardType,
                                    @RequestParam(value = "cardNm", required = false) String cardNm,
                                    @RequestParam(value = "cardSts", required = false) String cardSts,
                                    @RequestParam(value = "dailyAmtLimit", required = false) BigDecimal dailyAmtLimit,
                                    @RequestParam(value = "monthlyAmtLimit", required = false) BigDecimal monthlyAmtLimit,
                                    @RequestParam(value = "tradeAmtLimit", required = false) BigDecimal tradeAmtLimit) {
        Map<String, String> retMap = new HashMap<String, String>();

        CardRiskAudEntity cardRiskAudEntity = new CardRiskAudEntity();

        List<CardRiskAudEntity> cardRiskAudEntitytmp = new ArrayList<CardRiskAudEntity>();
        cardRiskAudEntitytmp = this.cardRiskAudService.get("0");
        for (CardRiskAudEntity c : cardRiskAudEntitytmp) {
            if ("0".equals(c.getAudSts())) {
                retMap.put("code", "9999");
                retMap.put("message", "基础设置存在未审核的记录!!!");
                return retMap;
            }
        }

        cardRiskAudEntity.setCardNo("0");
        cardRiskAudEntity.setTradeAmtLimit(tradeAmtLimit);
        cardRiskAudEntity.setDailyAmtLimit(dailyAmtLimit);
        cardRiskAudEntity.setMonthlyAmtLimit(monthlyAmtLimit);
        cardRiskAudEntity.setAppTm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        cardRiskAudEntity.setAudSts("0");
        try {
            this.cardRiskAudService.save(cardRiskAudEntity);
        } catch (Exception e) {
            logger.info("", e);
            retMap.put("code", "9999");
            retMap.put("message", "系统异常");
            return retMap;
        }

        retMap.put("code", "0000");
        return retMap;
    }

    /**
     * 银行卡风控信息修改方法
     *
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/update.do")
    @ResponseBody
    @OpLog(content = "银行卡限额规则更新,卡号:${cardNo}", allParams = false, type = OpLogTypeEnum.UPDATE)
    public Map<String, String> update(HttpServletRequest request,
                                      @RequestParam(value = "cardNo", required = false) String cardNo,
                                      @RequestParam(value = "cardType", required = false) String cardType,
                                      @RequestParam(value = "cardNm", required = false) String cardNm,
                                      @RequestParam(value = "cardSts", required = false) String cardSts,
                                      @RequestParam(value = "dailyAmtLimit", required = false) BigDecimal dailyAmtLimit,
                                      @RequestParam(value = "monthlyAmtLimit", required = false) BigDecimal monthlyAmtLimit,
                                      @RequestParam(value = "tradeAmtLimit", required = false) BigDecimal tradeAmtLimit) {

        Map<String, String> retMap = new HashMap<String, String>();
        List<CardRiskAudEntity> cardRiskAudEntitytmp = new ArrayList<CardRiskAudEntity>();
        cardRiskAudEntitytmp = this.cardRiskAudService.get(cardNo);
        for (int i = 0; i < cardRiskAudEntitytmp.size(); i++) {
            if (cardRiskAudEntitytmp != null && cardRiskAudEntitytmp.get(i).getAudSts().equals("0")) {
                retMap.put("code", "9999");
                retMap.put("message", "该卡号[" + cardNo + "]存在未审核的记录!!!");
                return retMap;
            }
            continue;
        }

        CardRiskAudEntity cardRiskAudEntity = new CardRiskAudEntity();
        cardRiskAudEntity.setCardNo(cardNo);
        cardRiskAudEntity.setCardNm(cardNm);
        cardRiskAudEntity.setCardSts(cardSts);
        cardRiskAudEntity.setCardType(cardType);
        cardRiskAudEntity.setTradeAmtLimit(tradeAmtLimit);
        cardRiskAudEntity.setDailyAmtLimit(dailyAmtLimit);
        cardRiskAudEntity.setMonthlyAmtLimit(monthlyAmtLimit);
        cardRiskAudEntity.setAppOpr(SpringSecurityUtil.getLoginUserName());
        cardRiskAudEntity.setAppTm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        cardRiskAudEntity.setAudOpr("");
        cardRiskAudEntity.setAudSts("0");
        // cardRiskAudEntity.setAudTm(new SimpleDateFormat("yyyy-MM-dd
        // HH:mm:ss").format(new Date()));
        try {
            this.cardRiskAudService.save(cardRiskAudEntity);
        } catch (Exception e) {
            logger.info("", e);
            retMap.put("code", "9999");
            retMap.put("message", "系统异常");
            return retMap;
        }
        retMap.put("code", "0000");
        return retMap;
    }



    @RequestMapping(value = "/queryById.do")
    public ModelAndView queryById(ModelAndView mav, String id, String cardNo) {
        ${g.entityName}Entity cardRiskMngEntity = null;
        if (id != null && !"".equals(id)) {
            cardRiskMngEntity = ${g.lowerEntityName}Service.get(id);
        }
        if ("0".equals(cardNo)) {
            cardRiskMngEntity = ${g.lowerEntityName}Service.queryByCardNo(cardNo);
            mav.setViewName(${strutil.toUpperCase(g.entityName)}_DEFAULT_VIEW);
        } else {
            mav.setViewName(${strutil.toUpperCase(g.entityName)}_ADD_UPDATE_VIEW);
        }

        mav.addObject("cardRiskMngEntity", cardRiskMngEntity);
        return mav;
    }

    /**
     * 银行卡风控信息文件导入方法
     *
     * @param request
     * @param param
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/importExcel.do")
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response, ModelAndView mav,
                                    @RequestParam("file") MultipartFile file) throws IOException {
        mav.setViewName(${strutil.toUpperCase(g.entityName)}_UPLOAD_VIEW);
        Map<String, Object> respMap = new HashMap<String, Object>();
        String msg = null;
        // 判断文件是否为空

        if (!file.isEmpty()) {
            try {
                CardRiskExcelReader tt = new CardRiskExcelReader();
                String name = file.getOriginalFilename();
                if (name.contains(".")) {
                    if ((name.substring(name.lastIndexOf(".") + 1, name.length())).equals("xls")) {
                        respMap = tt.readXls(file.getInputStream());

                    } else if (name.substring(name.lastIndexOf(".") + 1, name.length()).equals("xlsx")) {
                        respMap = tt.readXlsx(file.getInputStream());

                    } else {
                        mav.addObject("msg", "银行卡风控信息文件格式不对");
                        return mav;
                    }

                } else {
                    mav.addObject("msg", "银行卡风控信息文件格式不对");
                    return mav;
                }
            } catch (Exception e) {

                e.printStackTrace();
                mav.addObject("msg", "银行卡风控信息文件读取异常，请检查文件");
                return mav;
            }
        } else {
            mav.addObject("msg", "银行卡风控信息文件不能为空，请导入excel文件");
            return mav;
        }

        // 文件入库
        try {

            msg = this.${g.lowerEntityName}Service.saveFile(respMap);
        } catch (Exception ex) {
            ex.printStackTrace();
            mav.addObject("msg", "银行卡风控信息文件入库失败");
            return mav;
        }

        mav.addObject("msg", msg);
        return mav;
    }

    @RequestMapping(value = "/toUpload.do")
    public ModelAndView toUpload(ModelAndView mav) {
        mav.setViewName(${strutil.toUpperCase(g.entityName)}_UPLOAD_VIEW);

        return mav;
    }
}
