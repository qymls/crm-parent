package cn.nine.crm.web.controller;

import cn.nine.crm.domain.Guarantee;
import cn.nine.crm.query.GuaranteeQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/Guarantee")
@SuppressWarnings(value = "all")/*抑制警告*/
public class GuaranteeController extends BaseController<Guarantee, Long, GuaranteeQuery> {
}
