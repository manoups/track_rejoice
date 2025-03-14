package one.breece.track_rejoice.command.service

import one.breece.track_rejoice.command.command.ItemAnnouncementCommand
import one.breece.track_rejoice.core.command.ItemResponseCommand

interface ItemService:APBService<ItemAnnouncementCommand, ItemResponseCommand> {
}