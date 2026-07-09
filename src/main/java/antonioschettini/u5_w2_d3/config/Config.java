package antonioschettini.u5_w2_d3.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {
    @Value("${cloudinary.name}")
    private String cloudName;

    @Value("${cloudinary.apikey}")
    private String apiKey;

    @Value("${cloudinary.secret}")
    private String apiSecret;

    @Bean
    public Cloudinary uploaderCloudinary() {
        Map<String, String> configurazione = new HashMap<>();
        configurazione.put("cloud_name", cloudName);
        configurazione.put("api_key", apiKey);
        configurazione.put("api_secret", apiSecret);

        return new Cloudinary(configurazione);

    }
}
