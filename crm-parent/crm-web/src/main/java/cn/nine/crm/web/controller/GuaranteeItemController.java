package cn.nine.crm.web.controller;

import cn.nine.crm.domain.GuaranteeItem;
import cn.nine.crm.query.GuaranteeItemQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/GuaranteeItem")
@SuppressWarnings(value = "all")/*抑制警告*/
public class GuaranteeItemController extends BaseController<GuaranteeItem, Long, GuaranteeItemQuery> {
}
