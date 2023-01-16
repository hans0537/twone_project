package com.shinjin.twone.controller;

import com.shinjin.twone.common.commonMethod;
import com.shinjin.twone.dto.MemDTO;
import com.shinjin.twone.dto.ProjectDTO;
import com.shinjin.twone.service.MemService;
import com.shinjin.twone.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;
    @Autowired
    MemService memService;

    @RequestMapping("/project")
    public String viewProjectList(HttpServletRequest request, Model model){
        int memSeq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(memSeq);
        model.addAttribute("memDTO", memDTO);

        // team 테이블을 활용하여 내가 속한 프로젝트 가져오기
        List<ProjectDTO> plist = projectService.getList(memSeq);
        model.addAttribute("plist", plist);
        return "project/project";
    }

    @RequestMapping("/project/createForm")
    public String createProjectForm(HttpServletRequest request, Model model) {
        int mem_seq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(mem_seq);
        model.addAttribute("memDTO", memDTO);

        return "project/createForm";
    }

    @RequestMapping("/project/create")
    public String createProject(HttpServletRequest request, ProjectDTO projectDto, Model model) {
        int memSeq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(memSeq);
        model.addAttribute("memDTO", memDTO);

        // 프로젝트 생성
        projectDto.setMemSeq(memSeq);
        int projectSeq = projectService.createProject(projectDto);

        // 자동 팀 생성 (내가 만들었으니 관리자로)
        // 프로젝트 시퀀스 가져온걸 다시 보내기
        projectDto.setProjectSeq(projectSeq);
        projectService.insertMasterTeam(projectDto);
        
        return "redirect:/project";
    }

    @RequestMapping("/project/setting")
    public String projectSetting(HttpServletRequest request, Model model){
        int memSeq = (int) request.getSession().getAttribute("login");
        MemDTO memDTO = memService.getDto(memSeq);
        model.addAttribute("memDTO", memDTO);

        int projectSeq = Integer.parseInt(request.getParameter("projectSeq"));
        ProjectDTO pdto = projectService.selectOne(projectSeq);
        model.addAttribute("pdto", pdto);

        // 프로젝트 세팅 권한
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("memSeq", memSeq);
        map.put("projectSeq", projectSeq);
        int check = projectService.checkSetting(map);

        if(check == 1) {
            model.addAttribute("navType", "setting");
            return "project/setting";
        }else{
            String msg = "프로젝트 설정 권한이 없습니다. 관리자에게 문의 하세요";
            commonMethod.setAttribute(model, "/project/board?projectSeq=" + projectSeq, msg);
            return "common/alert";
        }

    }

    @RequestMapping("/project/delete")
    public String deleteProject(HttpServletRequest request){
        int projectSeq = Integer.parseInt(request.getParameter("projectSeq"));
        projectService.deleteOne(projectSeq);

        return "redirect:/project";
    }
}
