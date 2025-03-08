package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.BicycleAnnouncementCommand
import one.breece.track_rejoice.commands.BicycleResponseCommand

interface BicycleService: APBService<BicycleAnnouncementCommand, BicycleResponseCommand> {

}
