package io.github.ardonplay.normalserver.controllers;

import io.github.ardonplay.javatronilizer.models.DefaultModel;
import io.github.ardonplay.javatronilizer.models.Model;
import lombok.Getter;


@Getter
public class AbstractTemplateController {
    protected final String route;

    protected Model model = new DefaultModel();

    public AbstractTemplateController(String route){
        this.route = route;
    }

    public String onGet(){return null;}

}
