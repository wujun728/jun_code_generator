export default {
  messageUnreadCount: state => state.messageUnreadList.length,
  messageReadedCount: state => state.messageReadedList.length,
  messageTrashCount: state => state.messageTrashList.length
}
