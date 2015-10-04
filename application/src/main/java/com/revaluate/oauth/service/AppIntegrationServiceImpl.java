package com.revaluate.oauth.service;

import com.google.common.base.Splitter;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.domain.oauth.AppIntegrationScopeType;
import com.revaluate.domain.oauth.AppIntegrationType;
import com.revaluate.domain.oauth.AppSlackIntegrationDTO;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.persistence.AppIntegrationSlack;
import com.revaluate.oauth.persistence.AppIntegrationSlackRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Validated
public class AppIntegrationServiceImpl implements AppIntegrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppIntegrationSlackRepository oauthIntegrationSlackRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public AppIntegrationDTO createOauthIntegrationSlack(AppSlackIntegrationDTO appSlackIntegrationDTO, int userId) throws AppIntegrationException {
        //-----------------------------------------------------------------
        // There is already a user registered with this slack user id & team id
        // The relationship is 1 to many (user to many slack accounts)
        // and not same slack account to many users.
        //-----------------------------------------------------------------
        long slackUserIdAndTeamIdIsTaken = oauthIntegrationSlackRepository
                .countByAppIntegrationTypeAndSlackUserIdAndSlackTeamIdAndUserIdNot(AppIntegrationType.SLACK, appSlackIntegrationDTO.getUserId(), appSlackIntegrationDTO.getTeamId(), userId);
        if (slackUserIdAndTeamIdIsTaken > 0) {
            throw new AppIntegrationException("The user id and team id is already used by another user.");
        }

        //-----------------------------------------------------------------
        // Override existing
        //-----------------------------------------------------------------
        AppIntegrationSlack appIntegrationSlack = oauthIntegrationSlackRepository
                .findOneByAppIntegrationTypeAndSlackUserIdAndSlackTeamIdAndUserId(AppIntegrationType.SLACK, appSlackIntegrationDTO.getUserId(), appSlackIntegrationDTO.getTeamId(), userId)
                .orElseGet(AppIntegrationSlack::new);

        appIntegrationSlack.setAppIntegrationType(AppIntegrationType.SLACK);
        appIntegrationSlack.setAccessToken(appSlackIntegrationDTO.getAccessToken());

        makeSureScopeIsOfTypeIdentify(appSlackIntegrationDTO.getScopes());

        appIntegrationSlack.setAppIntegrationScopeType(AppIntegrationScopeType.IDENTIFY);
        appIntegrationSlack.setSlackUserId(appSlackIntegrationDTO.getUserId());
        appIntegrationSlack.setSlackTeamId(appSlackIntegrationDTO.getTeamId());
        appIntegrationSlack.setSlackTeamName(appSlackIntegrationDTO.getTeamName());
        appIntegrationSlack.setUser(userRepository.findOne(userId));

        return dozerBeanMapper.map(oauthIntegrationSlackRepository.save(appIntegrationSlack), AppIntegrationDTO.class);
    }

    public void makeSureScopeIsOfTypeIdentify(String scopes) throws AppIntegrationException {
        Splitter splitter = Splitter.on(',').omitEmptyStrings().trimResults();
        List<String> scopeAsString = splitter.splitToList(scopes);
        scopeAsString
                .stream()
                .filter(scopeEntry -> AppIntegrationScopeType.IDENTIFY.name().toLowerCase().equals(scopeEntry))
                .findFirst()
                .orElseThrow(() -> new AppIntegrationException("The access scope should be identify"));
    }

    @Override
    public List<AppIntegrationDTO> findAllIntegrations(int userId) throws AppIntegrationException {

        return collectAndGet(oauthIntegrationSlackRepository.findAllByAppIntegrationTypeAndUserId(AppIntegrationType.SLACK, userId));
    }

    @Override
    public void removeIntegration(int appIntegrationId, int userId) throws AppIntegrationException {

        oauthIntegrationSlackRepository.deleteAllByAppIntegrationIdAndUserId(appIntegrationId, userId);
    }

    private List<AppIntegrationDTO> collectAndGet(List<AppIntegrationSlack> appIntegrations) {
        return appIntegrations
                .stream()
                .map(category -> dozerBeanMapper.map(category, AppIntegrationDTO.class))
                .collect(Collectors.toList());
    }
}