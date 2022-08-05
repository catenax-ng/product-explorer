import spock.lang.Specification

class SpockSpec extends Specification {

    def "should pass"() {
        given:
        int i = 0;
        when:
        i += 1
        then:
        i == 1
    }
}
