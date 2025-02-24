package one.breece.track_rejoice.web.controller

import one.breece.track_rejoice.service.PetService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/announcements")
class APBManagerController(
    private val petService: PetService,
) {
    @GetMapping(value = ["","/"])
    fun announcements(model: Model): String {
        model.addAttribute("pets", petService.findAll())
        return "announcements"
    }

    @DeleteMapping("/{id}")
    fun deleteAnnouncement(@PathVariable("id") id: Long): String {
        petService.deleteById(id)
        return "redirect:/announcements"
    }
}