package org.jra.mabanque.web;

import org.jra.mabanque.services.IBanqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BanqueController
{
  @Autowired
  private IBanqueService banqueService;

  @RequestMapping("/comptes")
  public String index(){
    return "comptes";
  }
}
