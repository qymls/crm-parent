package cn.nine.crm.web.controller;

import cn.nine.crm.domain.GuaranteeItem;
import cn.nine.crm.query.GuaranteeItemQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/guaranteeItem")
public class GuaranteeItemController extends BaseController<GuaranteeItem, Long, GuaranteeItemQuery> {
}
