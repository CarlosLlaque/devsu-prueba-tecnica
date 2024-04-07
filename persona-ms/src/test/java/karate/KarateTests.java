package karate;

import com.cllaque.personams.PersonaMsApplication;
import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {PersonaMsApplication.class})
class KarateTests {

    @Karate.Test
    Karate createPersonTest() {
        return Karate.run("classpath:karate/cliente/create.feature");
    }

}
