package com.tertir.tertiram.service;

import com.tertir.tertiram.rest.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SievnService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Initiates session
     *
     * @param login    login
     * @param password password
     * @return authorization token
     * @throws Exception if response code is not 200
     */
    public String initiate(String login, String password) throws Exception {

        URI expand = this.restTemplate.getUriTemplateHandler().expand("/login");

        try {
            ResponseEntity<?> responseEntity =
                    this.restTemplate.postForEntity(expand, new LoginForm(login, password), ResponseEntity.class);

            return responseEntity.getHeaders().getFirst("Authorization");

        } catch (Exception e) {
            throw new Exception("Неправильное имя пользователя или пароль.");
        }

    }

    /**
     * Finds all users
     *
     * @param authorizationToken token
     * @return List of users
     * @throws Exception if response code is not 200
     */
    public List<ResponseModelAdmin> getAllAdminData(String authorizationToken) throws Exception {

        URI expand = this.restTemplate.getUriTemplateHandler().expand("/sievn/management/getAllDataAdmin");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        try {

            return this.restTemplate.exchange(expand,
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<List<ResponseModelAdmin>>() {
                    }
            ).getBody();

        } catch (Exception e) {
            throw new Exception("Не удалось загрузить данные. Попробуйте позже.");
        }
    }


    public void changePassword(String oldPassword, String newPassword, String authorizationToken) throws Exception {

        URI expand = this.restTemplate.getUriTemplateHandler().expand("/sievn/management/password/change");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromUri(expand)
                .queryParam("oldPassword", oldPassword)
                .queryParam("newPassword", newPassword)
                .encode().toUriString();

        try {
            this.restTemplate.put(urlTemplate, requestEntity);
        } catch (Exception e) {
            throw new Exception(String.format("Невозможно изменить пароль. Попробуйте позже։ %s", e.getMessage()));
        }
    }

    public void addHouseData(RequestModel requestModel, String authorizationToken) throws Exception {

        URI expand = this.restTemplate.getUriTemplateHandler().expand("/sievn/management/addHouseData1");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationToken);

        HttpEntity<RequestModel> httpEntity = new HttpEntity<>(requestModel, headers);

        try {
            restTemplate.postForEntity(expand, httpEntity, Void.class);
        } catch (Exception e) {
            throw new Exception(String.format("Не удалось добавить. Попробуйте позже։ %s", e.getMessage()));
        }
    }

    public void editHouseData(RequestModelForUpdate requestModel, String authorizationToken) throws Exception {
        URI expand = this.restTemplate.getUriTemplateHandler().expand("/sievn/management/updateHouseData1");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationToken);

        HttpEntity<RequestModelForUpdate> httpEntity = new HttpEntity<>(requestModel, headers);

        try {
            restTemplate.postForEntity(expand, httpEntity, Void.class);
        } catch (Exception e) {
            throw new Exception(String.format("Не удалось oбновить. Попробуйте позже։ %s", e.getMessage()));
        }

    }

    public void deleteHouseInfo(String id, String authorizationToken) throws Exception {

        URI expand = this.restTemplate.getUriTemplateHandler().expand("/sievn/management/deleteDataById");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationToken);

        HttpEntity<DeleteModel> httpEntity = new HttpEntity<>(new DeleteModel(Long.valueOf(id)), headers);

        try {
            restTemplate.exchange(expand, HttpMethod.DELETE, httpEntity, Void.class);
        } catch (Exception e) {
            throw new Exception(String.format("Невозможно удалить. Попробуйте позже։ %s", e.getMessage()));
        }
    }

    public ResponseModelAdmin getHouseDataById(String id, String authorizationToken) throws Exception {

        URI expand = this.restTemplate.getUriTemplateHandler().expand("/sievn/management/getDataAdminById");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromUri(expand)
                .queryParam("houseId", "{houseId}")
                .encode()
                .toUriString();
        Map<String, Long> params = new HashMap<>();
        params.put("houseId", Long.valueOf(id));

        try {
            return this.restTemplate.exchange(
                    urlTemplate, HttpMethod.GET, entity, ResponseModelAdmin.class, params).getBody();
        } catch (Exception e) {
            throw new Exception(String.format("Предоставлена неверная информация. Попробуйте позже։ %s", e.getMessage()));
        }

    }


    public List<Image> getAllImages(Long houseId, List<String> pictures, String authorizationToken) throws Exception {

        URI expand = this.restTemplate.getUriTemplateHandler().expand(
                "/sievn/public/files");

        List<Image> byteArrayResources = new ArrayList<>();

        for (String fileName :
                pictures) {
            try {
                String s = UriComponentsBuilder.fromUri(expand).toUriString()+ "/?houseId=%s&fileName=%s";
                String format = String.format(s, houseId, fileName);
                BufferedImage image = ImageIO.read(new URL(format));
                Image imageFx = SwingFXUtils.toFXImage(image, null);

                byteArrayResources.add(imageFx);
                //byteArrayResources.add(bytes);
            } catch (Exception e) {
                throw new Exception(String.format("Предоставлена неверная информация. Попробуйте позже։ %s", e.getMessage()));
            }
        }


        return byteArrayResources;
    }
}
