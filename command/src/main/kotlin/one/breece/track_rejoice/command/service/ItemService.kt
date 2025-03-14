package one.breece.track_rejoice.command.service

import one.breece.track_rejoice.command.command.ItemAnnouncementCommand
import one.breece.track_rejoice.command.command.ItemResponseCommand

interface ItemService:APBService<ItemAnnouncementCommand, ItemResponseCommand> {
}