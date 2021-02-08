package informative.rest

import grails.rest.RestfulController

import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalTime

class VisitedController extends RestfulController {
    static responseFormats = ['json', 'xml']

    VisitedController() {
        super(Visited)
    }

    def count() {
        respond([count: Visited.count])
    }

    def index() {
        def weekRange = 0..6
        def result = weekRange.stream().mapToInt { day ->
            long start = Timestamp.valueOf(LocalDate.now().minusDays(day).atTime(LocalTime.MIN)).getTime()
            long end = Timestamp.valueOf(LocalDate.now().minusDays(day).atTime(LocalTime.MAX)).getTime()
            List<Visited> vList = Visited.findAllByTimeStampBetween(start, end)
            vList.size()
        }.collect()
        print "result ${result}"
        respond result
    }
}
