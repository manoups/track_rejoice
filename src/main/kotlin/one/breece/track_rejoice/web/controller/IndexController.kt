package one.breece.track_rejoice.web.controller

import jakarta.validation.Valid
import one.breece.track_rejoice.commands.UserPositionCommand
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController {
    @GetMapping(value = ["","/","/index","/index/"])
    fun search(@RequestParam(defaultValue = "search") target: String, model: Model): String {
        model.addAttribute("locationCommand", UserPositionCommand())
        model.addAttribute("target", target)
        return "landing"
    }

    @PostMapping("/process")
    fun land(
        @Valid position: UserPositionCommand, modelMap: ModelMap, bindingResult: BindingResult, model: Model
    ): ModelAndView {
        modelMap.addAttribute("lon", position.lon)
        modelMap.addAttribute("lat", position.lat)
        modelMap.addAttribute("myLon", position.lon)
        modelMap.addAttribute("myLat", position.lat)
        modelMap.addAttribute("zoom", position.zoom)
        modelMap.addAttribute("identify", position.geoAvailable)
        return ModelAndView("redirect:/search", modelMap)
    }
}