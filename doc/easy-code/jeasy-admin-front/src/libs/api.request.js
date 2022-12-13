import HttpRequest from '@/libs/httpRequest'
import config from '@/config'

const baseUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro

const httpRequest = new HttpRequest(baseUrl)
export default httpRequest
