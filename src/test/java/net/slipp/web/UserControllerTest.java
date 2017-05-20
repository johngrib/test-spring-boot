package net.slipp.web;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by johngrib on 2017. 5. 18..
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired private TestRestTemplate template;
    @Autowired private UserRepository userRepository;

    private User testUser;

    @Before
    public void prepareTestUser() {
        testUser = userRepository.save(new User("testUser", "password", "name", "john@grib"));
    }

    @Test
    public void create() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("userId", "javajigi");
        params.add("password", "pass");
        params.add("name", "재성");
        params.add("email", "javajigi@slipp.net");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        ResponseEntity<String> response = template.postForEntity("/users", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
    }

    @Test
    public void 사용자_목록이_출력되어야_한다() throws Exception {
        final ResponseEntity<String> response = template.getForEntity("/users", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(testUser.getEmail()), is(true));
        assertThat(response.getBody().contains(testUser.getName()), is(true));
    }

    @Test
    public void 회원정보_수정화면이_나와야_한다() throws Exception {
        final ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", testUser.getId()), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(testUser.getEmail()), is(true));
    }

    @Test
    public void 회원정보_수정이_가능해야_한다() throws Exception {

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        params.add("_method", "put");
        params.add("password", testUser.getPassword());
        params.add("name", "변경된 이름");
        params.add("email", "modified@email");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        ResponseEntity<String> response = template.postForEntity(
                String.format("/users/%d", testUser.getId()),
                request,
                String.class
        );

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));

    }

    @Test
    public void 비밀번호가_다르면_회원정보_수정_불가능() throws Exception {

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        params.add("_method", "put");
        params.add("password", testUser.getPassword() + "asdf");
        params.add("name", "변경된 이름");
        params.add("email", "modified@email");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        ResponseEntity<String> response = template.postForEntity(
                String.format("/users/%d", testUser.getId()),
                request,
                String.class
        );

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));

    }

    @After
    public void endTest() {
        userRepository.delete(testUser);
    }
}