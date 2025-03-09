package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.service.BoloService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

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
}