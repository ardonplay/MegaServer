package io.github.ardonplay.normalserver.controllers;

public class MainController extends AbstractTemplateController{
    public MainController() {
        super("/files");
    }

    @Override
    public String onGet(){
        model.addAttribute("title", "It works TITLE");
        model.addAttribute("help message", "This is test page of cringe server");
        return "/index.html";
    }
}
