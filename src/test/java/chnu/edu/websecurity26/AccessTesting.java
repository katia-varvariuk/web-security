package chnu.edu.websecurity26;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccessTesting {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // 1. Анонімний користувач на захищений роут (Очікується 401 Unauthorized)
    @Test
    public void testAnonymousAccessToProtected() throws Exception {
        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isUnauthorized());
    }

    // 2. Доступ публічного роута без авторизації (Очікується 200 OK)
    @Test
    public void testPublicRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/unknown"))
                .andExpect(status().isOk());
    }

    // 3. Admin accessing admin route (Очікується 200 OK)
    @Test
    public void testAdminAccessToAdminRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/admin")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Admin!"));
    }

    // 4. User trying to access admin route (Очікується 403 Forbidden)
    @Test
    public void testUserAccessToAdminRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/admin")
                        .with(httpBasic("user", "user")))
                .andExpect(status().isForbidden());
    }

    // 5. User accessing user route (Очікується 200 OK)
    @Test
    public void testUserAccessToUserRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/user")
                        .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello User!"));
    }

    // 6. Admin trying to access user route (Очікується 403 Forbidden - якщо обмежено лише роллю USER)
    @Test
    public void testAdminAccessToUserRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/user")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isForbidden());
    }

    // 7. Man route accessed by User (Очікується 200 OK)
    @Test
    public void testUserAccessToManRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/man")
                        .with(httpBasic("user", "user")))
                .andExpect(status().isOk());
    }

    // 8. Man route accessed by Admin (Очікується 200 OK)
    @Test
    public void testAdminAccessToManRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/man")
                        .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk());
    }

    // 9. Superadmin accessing admin route (Очікується 200 OK)
    @Test
    public void testSuperadminAccessToAdminRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/admin")
                        .with(httpBasic("superadmin", "superadmin")))
                .andExpect(status().isOk());
    }

    // 10. Superadmin accessing user route (Очікується залежно від налаштувань, наприклад Forbidden якщо тільки USER)
    @Test
    public void testSuperadminAccessToUserRoute() throws Exception {
        mockMvc.perform(get("/api/v1/items/hello/user")
                        .with(httpBasic("superadmin", "superadmin")))
                .andExpect(status().isForbidden());
    }
}