package one.breece.track_rejoice.command.service

import one.breece.track_rejoice.command.command.BicycleAnnouncementCommand
import one.breece.track_rejoice.core.command.BicycleResponseCommand

interface BicycleService: APBService<BicycleAnnouncementCommand, BicycleResponseCommand> {

}
