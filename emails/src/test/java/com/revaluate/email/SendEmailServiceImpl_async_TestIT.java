package com.revaluate.email;

import com.microtripit.mandrillapp.lutung.controller.MandrillMessagesApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.email.EmailStatus;
import com.revaluate.domain.email.EmailType;
import com.revaluate.domain.email.SendTo;
import com.revaluate.domain.email.SendToBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__emailApi__test.xml")
public class SendEmailServiceImpl_async_TestIT {

    @InjectMocks
    private SendEmailServiceImpl sendEmailService;

    @Mock
    private MandrillService mandrillService;

    @Spy
    private ConfigProperties configProperties;

    @Test
    public void sendAsyncEmailTo__sentExpected__IsOk() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = Mockito.mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = Mockito.mock(MandrillMessageStatus.class);
        Mockito.when(mandrillMessageStatus.getStatus()).thenReturn(EmailStatus.QUEUED.getStatus());

        Mockito.when(mandrillMessagesApi.sendTemplate(anyString(), anyMapOf(String.class, String.class), anyObject(), eq(Boolean.TRUE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        Mockito.when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        SendTo sendTo = new SendToBuilder().withEmail("a@b.c").withEmailToken("abcdef").withEmailTokenId(1).withEmailType(EmailType.CREATED_ACCOUNT).withFirstName("a").withId(1).withLastName("").build();
        EmailStatus feedback = sendEmailService.sendAsyncEmailTo(sendTo);
        assertThat(feedback, is(EmailStatus.QUEUED));
    }

    @Test(expected = SendEmailException.class)
    public void sendAsyncEmailTo__sentExpectedButReturnedQueued__exceptionThrown() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = Mockito.mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = Mockito.mock(MandrillMessageStatus.class);
        Mockito.when(mandrillMessageStatus.getStatus()).thenReturn(EmailStatus.SENT.getStatus());

        Mockito.when(mandrillMessagesApi.sendTemplate(anyString(), anyMapOf(String.class, String.class), anyObject(), eq(Boolean.TRUE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        Mockito.when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        SendTo sendTo = new SendToBuilder().withEmail("a@b.c").withEmailToken("abcdef").withEmailTokenId(1).withEmailType(EmailType.CREATED_ACCOUNT).withFirstName("a").withId(1).withLastName("").build();
        EmailStatus feedback = sendEmailService.sendAsyncEmailTo(sendTo);
        assertThat(feedback, is(EmailStatus.QUEUED));
    }


    @Test(expected = SendEmailException.class)
    public void sendAsyncEmailTo__sentExpectedButReturnedRejected__exceptionThrown() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = Mockito.mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = Mockito.mock(MandrillMessageStatus.class);
        Mockito.when(mandrillMessageStatus.getStatus()).thenReturn(EmailStatus.REJECTED.getStatus());

        Mockito.when(mandrillMessagesApi.sendTemplate(anyString(), anyMapOf(String.class, String.class), anyObject(), eq(Boolean.TRUE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        Mockito.when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        SendTo sendTo = new SendToBuilder().withEmail("a@b.c").withEmailToken("abcdef").withEmailTokenId(1).withEmailType(EmailType.CREATED_ACCOUNT).withFirstName("a").withId(1).withLastName("").build();
        EmailStatus feedback = sendEmailService.sendAsyncEmailTo(sendTo);
        assertThat(feedback, is(EmailStatus.QUEUED));
    }
}