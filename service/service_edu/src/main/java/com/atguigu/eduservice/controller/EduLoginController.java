package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author zsh
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    /** login
     *
     * @return
     */
    @PostMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }

    /**info
     *
     * @return
     */
    @GetMapping("/info")
    public R info() {

        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
