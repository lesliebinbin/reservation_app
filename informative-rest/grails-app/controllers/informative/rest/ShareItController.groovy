package informative.rest


import grails.rest.*
import grails.converters.*

class ShareItController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ShareItController() {
        super(ShareIt)
    }

    def index(){
        respond(ShareIt.findAll())
    }
}
