package informative.rest

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class SubmittedServiceSpec extends Specification {

    SubmittedService submittedService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Submitted(...).save(flush: true, failOnError: true)
        //new Submitted(...).save(flush: true, failOnError: true)
        //Submitted submitted = new Submitted(...).save(flush: true, failOnError: true)
        //new Submitted(...).save(flush: true, failOnError: true)
        //new Submitted(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //submitted.id
    }

    void "test get"() {
        setupData()

        expect:
        submittedService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Submitted> submittedList = submittedService.list(max: 2, offset: 2)

        then:
        submittedList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        submittedService.count() == 5
    }

    void "test delete"() {
        Long submittedId = setupData()

        expect:
        submittedService.count() == 5

        when:
        submittedService.delete(submittedId)
        sessionFactory.currentSession.flush()

        then:
        submittedService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Submitted submitted = new Submitted()
        submittedService.save(submitted)

        then:
        submitted.id != null
    }
}
