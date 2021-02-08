package informative.rest


import grails.rest.*
import grails.converters.*

class ContactController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ContactController() {
        super(Contact)
    }


    def count(){
        respond([count:Contact.count])
    }

    def index(){
        respond(Contact.findAll())
    }


}
