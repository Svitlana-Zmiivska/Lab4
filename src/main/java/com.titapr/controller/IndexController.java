package com.titapr.controller;

import com.titapr.processor.TabProcessor1;
import com.titapr.processor.TabProcessor2;
import com.titapr.processor.TabProcessor3;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@Controller
public class IndexController {

    private static final String VIEW_NAME__INDEX = "index";

    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);


    @Autowired
    private TabProcessor1 tabProcessor1;

    @Autowired
    private TabProcessor2 tabProcessor2;

    @Autowired
    private TabProcessor3 tabProcessor3;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getTable(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        String tabId = request.getParameter("tabId");
        if (StringUtils.isEmpty(tabId)) {
            response.sendRedirect("/index?tabId=1");
            return modelAndView;
        }
        switch (tabId) {
            case "1": {
                tabProcessor1.processGet(request);
                break;
            }
            case "2" : {
                tabProcessor2.processGet(request);
                break;
            }
            case "3" : {
                tabProcessor3.processGet(request);
                break;
            }
            default: {
                response.sendRedirect("/index?tabId=1");
                return modelAndView;
            }
        }
        modelAndView.setViewName(VIEW_NAME__INDEX);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/tab1/postLpr", method = RequestMethod.POST)
    public void postLpr(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String name = request.getParameter("name");
            int range = Integer.parseInt(request.getParameter("range"));
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                tabProcessor1.processEdit(Long.parseLong(id), name, range);
            } else {
                tabProcessor1.processCreate(name, range);
            }
        } catch (Exception e) {
            LOGGER.error("Cannot process change", e);
        }
        response.sendRedirect("/index?tabId=1");
    }

    @ResponseBody
    @RequestMapping(value = "/tab1/reqDelete", method = RequestMethod.POST)
    public void postDeleteLpr(@RequestBody String requestBody) {
        JSONObject json = constructJsonFromInput(requestBody);
        try {
            long id = (Integer) json.get("id");
            tabProcessor1.processDelete(id);
        } catch (Exception e) {
            LOGGER.error("Cannot process action", e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/tab2/postAlternative", method = RequestMethod.POST)
    public void postAlternative(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String name = request.getParameter("name");
            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {
                tabProcessor2.processEdit(Long.parseLong(id), name);
            } else {
                tabProcessor2.processCreate(name);
            }
        } catch (Exception e) {
            LOGGER.error("Cannot process change", e);
        }
        response.sendRedirect("/index?tabId=2");
    }

    @ResponseBody
    @RequestMapping(value = "/tab2/reqDelete", method = RequestMethod.POST)
    public void postDeleteAlternative(@RequestBody String requestBody) {
        JSONObject json = constructJsonFromInput(requestBody);
        try {
            long id = (Integer) json.get("id");
            tabProcessor2.processDelete(id);
        } catch (Exception e) {
            LOGGER.error("Cannot process action", e);
        }
    }

    @RequestMapping(value = "/tab3/selectLpr", method = RequestMethod.POST)
    public void selectLpr(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String id = request.getParameter("newLpr");
            tabProcessor3.processLPR(Long.parseLong(id), request);
        } catch (Exception e) {
            LOGGER.error("Cannot process change", e);
        }
        response.sendRedirect("/index?tabId=3");
    }

    @RequestMapping(value = "/tab3/vote", method = RequestMethod.POST)
    public void vote(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            if(tabProcessor3.processVotes(request, response)) {
                return;
            }
        } catch (Exception e) {
            LOGGER.error("Cannot process change", e);
        }
        response.sendRedirect("/index?tabId=3");
    }

    private JSONObject constructJsonFromInput(String jsonPayload) {
        try {
            return new JSONObject(jsonPayload);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Wrong JSON payload is obtained");
        }
    }
}
