package com.titapr.processor;

import com.titapr.bean.LPR;
import com.titapr.data.LprDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TabProcessor1 {

    @Autowired
    private LprDAO lprDAO;

    public void processGet(HttpServletRequest request) {
        List<LPR> lps = lprDAO.getAllLpr();
        request.setAttribute("allLps", lps);
        if(request.getParameter("edit") != null) {
            long num = Long.parseLong(request.getParameter("edit"));
            LPR editTarget = lprDAO.getLprById(num);
            request.setAttribute("editTarget", editTarget);
        }
    }

    public void processCreate(String name, int range) {
        LPR lpr = new LPR();
        lpr.setName(name);
        lpr.setRange(range);
        lprDAO.createLpr(lpr);
    }

    public void processEdit(long id, String name, int range) {
        LPR lpr = new LPR();
        lpr.setNum(id);
        lpr.setName(name);
        lpr.setRange(range);
        lprDAO.updateLpr(lpr);
    }

    public void processDelete(long id) {
        lprDAO.deleteLpr(id);
    }
}
