package cn.nine.crm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseDomain {

    private String name;
    private String sn;
    //子部门
    private List<Department> children = new ArrayList<>();
    //父部门
    private Long prentId;
    private Employee manager;
}
