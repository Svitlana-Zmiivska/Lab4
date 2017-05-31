package com.ds.processor;

import com.ds.bean.*;
import com.ds.data.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class TabProcessor3 {

    @Autowired
    private AlternativeDAO alternativeDAO;

    @Autowired
    private LprDAO lprDAO;

    public void processGet(HttpServletRequest request) {
        List<Alternative> alternatives = alternativeDAO.getAllAlternative();
        request.setAttribute("allAlternatives", alternatives);
        LPR lpr = (LPR) request.getSession().getAttribute("currentLpr");
        if (lpr == null) {
            lpr = lprDAO.getLprById(0);
            request.getSession().setAttribute("currentLpr", lpr);
        }
        List<LPR> lprs = lprDAO.getAllLpr();
        lprs.remove(lpr);
        request.setAttribute("selectLpr", lprs);
    }

    public void processLPR(long id, HttpServletRequest request) {
        LPR lpr = lprDAO.getLprById(id);
        request.getSession().setAttribute("currentLpr", lpr);
    }

    public boolean processVotes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Alternative> alternatives = alternativeDAO.getAllAlternative();
        List<Integer> ranges = new ArrayList<>();
        int sum = 0;
        for (Alternative alternative : alternatives) {
            String param = request.getParameter("r" + alternative.getNum());
            if (StringUtils.isEmpty(param)) {
                response.sendRedirect("/index?tabId=3&errorCode=1");
                return true;
            }
            int a = Integer.parseInt(param);
            ranges.add(a);
            sum += a;
        }
        if (sum != asum(alternatives.size())) {
            response.sendRedirect("/index?tabId=3&errorCode=1");
            return true;
        }
        @SuppressWarnings("unchecked")
        Map<Long, List<Integer>> sranges = (Map<Long, List<Integer>>) request.getSession().getAttribute("ranges");
        if (sranges == null) {
            sranges = new HashMap<>();
            request.getSession().setAttribute("ranges", sranges);
        }
        LPR lpr = (LPR) request.getSession().getAttribute("currentLpr");
        sranges.put(lpr.getNum(), ranges);

        // 1
        Map<Long, Integer> res1 = new HashMap<>();
        for (Alternative a : alternatives) {
            res1.put(a.getNum(), 0);
        }
        for (int i = 0; i < alternatives.size() - 1; ++i) {
            for (int j = i + 1; j < alternatives.size(); ++j) {
                for (Map.Entry<Long, List<Integer>> r : sranges.entrySet()) {
                    int o1 = r.getValue().get(i);
                    int o2 = r.getValue().get(j);
                    if (o1 > o2) {
                        int re = res1.get(alternatives.get(j).getNum());
                        res1.put(alternatives.get(j).getNum(), re + 1);
                    } else if (o1 < o2) {
                        int re = res1.get(alternatives.get(i).getNum());
                        res1.put(alternatives.get(i).getNum(), re + 1);
                    }
                }
            }
        }
        Map<Long, Boolean> res11 = new HashMap<>();
        for (Alternative a : alternatives) {
            res11.put(a.getNum(), true);
        }
        for (int i = 0; i < alternatives.size() - 1; ++i) {
            for (int j = i + 1; j < alternatives.size(); ++j) {
                int o1 = res1.get(alternatives.get(i).getNum());
                int o2 = res1.get(alternatives.get(j).getNum());
                if (o1 > o2) {
                    res11.put(alternatives.get(j).getNum(), false);
                } else if (o1 < o2) {
                    res11.put(alternatives.get(i).getNum(), false);
                }
            }
        }
        boolean found = false;
        for (Map.Entry<Long, Boolean> e : res11.entrySet()) {
            if (e.getValue()){
                if (found) {
                    request.getSession().setAttribute("winner1", "Не визначено");
                    break;
                }
                found = true;
                request.getSession().setAttribute("winner1", alternativeDAO.getAlternativeById(e.getKey()).getName());
            }
        }
        if (!found) {
            request.getSession().setAttribute("winner1", "Не визначено");
        }

        // 2
        Map<Long, Integer> res2 = new HashMap<>();
        for (Alternative a : alternatives) {
            res2.put(a.getNum(), 0);
        }
        for (int i = 0; i < alternatives.size() - 1; ++i) {
            for (int j = i + 1; j < alternatives.size(); ++j) {
                int o1 = res1.get(alternatives.get(i).getNum());
                int o2 = res1.get(alternatives.get(j).getNum());
                int r1 = res2.get(alternatives.get(i).getNum());
                int r2 = res2.get(alternatives.get(j).getNum());
                if (o1 > o2) {
                    res2.put(alternatives.get(i).getNum(), r1 - 1);
                    res2.put(alternatives.get(j).getNum(), r2 + 1);
                } else if (o1 < o2) {
                    res2.put(alternatives.get(i).getNum(), r1 + 1);
                    res2.put(alternatives.get(j).getNum(), r2 - 1);
                }
            }
        }
        Map.Entry<Long, Integer> min = null;
        int count = 1;
        for (Map.Entry<Long, Integer> s : res2.entrySet()) {
            if (min == null) {
                min = s;
                continue;
            }
            if (min.getValue().equals(s.getValue())) {
                ++count;
                continue;
            }
            if (min.getValue() > s.getValue()) {
                min = s;
                count = 1;
            }
        }
        if (count > 1) {
            request.getSession().setAttribute("winner2", "Не визначено");
        } else if (min != null) {
            request.getSession().setAttribute("winner2", alternativeDAO.getAlternativeById(min.getKey()).getName());
        }



        return false;
    }

    private int asum(int size){
        return ((1 + size) * size) / 2;
    }
}
