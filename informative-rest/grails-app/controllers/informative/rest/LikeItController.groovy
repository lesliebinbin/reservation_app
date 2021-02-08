package informative.rest


import grails.rest.*
import grails.converters.*

class LikeItController extends RestfulController {
    static responseFormats = ['json', 'xml']
    LikeItController() {
        super(LikeIt)
    }
    def index(){
        respond(LikeIt.findAll())
    }
}
