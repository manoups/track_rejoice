package one.breece.track_rejoice.command.web.controller

import one.breece.track_rejoice.command.service.BoloService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/announcements")
class APBManagerController(
    private val boloService: BoloService,
) {
    @GetMapping(value = ["","/"])
    fun announcements(model: Model, pageable: Pageable): String {
//        TODO: change model attribute name
        model.addAttribute("bolos", boloService.findAll(pageable))
        return "announcements"
    }

    @DeleteMapping("/{sku}")
    fun deleteAnnouncement(@PathVariable("sku") sku: UUID): String {
        boloService.deleteBySku(sku)
        return "redirect:/announcements"
    }

    @PostMapping("/mark/found/{sku}")
    fun markFoundAnnouncement(@PathVariable("sku") sku: UUID): String {
        boloService.markFoundBySku(sku)
        return "redirect:/announcements"
    }
}