package com.qccr.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qccr.pojo.TestReport;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReportVO {

    private Integer id;
    private String name; //套件名
    private String username;//测试人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createReportTime;//生成时间

    private int totalCase;//总用例数，计算通过率
    private int successes;//成功通过率
    private int failures;//失败率

    private List<CaseListVO> caseList;

    public int getTotalCase() {
        return caseList.size();

    }

    public int getSuccesses() {
        int count1 = 0, count2 = 0;
        for (CaseListVO caseListVO : caseList) {
            TestReport report = caseListVO.getTestReport();
            if (report != null) {
                if (report.getPassFlag().equals("通过")) {
                    count1++;
                } else {
                    count2++;
                }
            }
        }
        this.successes = count1;
        this.failures = count2;
        return successes;
    }

    public int getFailures() {
        return failures;
    }
}
