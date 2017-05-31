package com.ds.processor;

import com.ds.bean.Alternative;
import com.ds.bean.LPR;
import com.ds.data.AlternativeDAO;
import com.ds.data.LprDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TabProcessor2 {

    @Autowired
    private AlternativeDAO alternativeDAO;

    public void processGet(HttpServletRequest request) {
        List<Alternative> alternative = alternativeDAO.getAllAlternative();
        request.setAttribute("allAlternatives", alternative);
        if(request.getParameter("edit") != null) {
            long num = Long.parseLong(request.getParameter("edit"));
            Alternative editTarget = alternativeDAO.getAlternativeById(num);
            request.setAttribute("editAlternative", editTarget);
        }
    }

    public void processCreate(String name) {
        Alternative alternative = new Alternative();
        alternative.setName(name);
        alternativeDAO.createAlternative(alternative);
    }

    public void processEdit(long id, String name) {
        Alternative alternative = new Alternative();
        alternative.setNum(id);
        alternative.setName(name);
        alternativeDAO.updateAlternative(alternative);
    }

    public void processDelete(long id) {
        alternativeDAO.deleteAlternative(id);
    }
}
