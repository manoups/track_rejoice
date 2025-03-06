package one.breece.track_rejoice.service

import one.breece.track_rejoice.commands.ItemAnnouncementCommand
import one.breece.track_rejoice.commands.ItemResponseCommand

interface ItemService:APBService<ItemAnnouncementCommand, ItemResponseCommand> {
}