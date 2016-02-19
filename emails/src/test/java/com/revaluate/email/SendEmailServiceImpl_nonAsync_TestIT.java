package com.revaluate.email;

import com.microtripit.mandrillapp.lutung.controller.MandrillMessagesApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.contact.ContactDTO;
import com.revaluate.domain.contact.ContactDTOBuilder;
import com.revaluate.domain.email.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext__emailApi__test.xml")
public class SendEmailServiceImpl_nonAsync_TestIT {

    @InjectMocks
    private SendEmailServiceImpl sendEmailService;

    @Mock
    private MandrillService mandrillService;

    @Spy
    @Autowired
    private ConfigProperties configProperties;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sendNonAsyncEmailTo__sentExpected__isOk() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = mock(MandrillMessageStatus.class);
        when(mandrillMessageStatus.getStatus()).thenReturn(MandrillEmailStatus.SENT.getStatus());

        when(mandrillMessagesApi.sendTemplate(anyString(), anyMapOf(String.class, String.class), anyObject(), eq(Boolean.FALSE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        SendTo sendTo = new SendToBuilder().withEmail("a@b.c").withEmailToken("abcdef").withEmailId(1).withEmailType(EmailType.CREATED_ACCOUNT).withFirstName("a").withId(1).withLastName("").build();
        MandrillEmailStatus feedback = sendEmailService.sendNonAsyncEmailTo(sendTo);
        assertThat(feedback).isEqualTo((MandrillEmailStatus.SENT));
    }

    @Test(expected = SendEmailException.class)
    public void sendNonAsyncEmailTo__sentExpectedButReturnedQueued__exceptionThrown() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = mock(MandrillMessageStatus.class);
        when(mandrillMessageStatus.getStatus()).thenReturn(MandrillEmailStatus.QUEUED.getStatus());

        when(mandrillMessagesApi.sendTemplate(anyString(), anyMapOf(String.class, String.class), anyObject(), eq(Boolean.FALSE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        SendTo sendTo = new SendToBuilder().withEmail("a@b.c").withEmailToken("abcdef").withEmailId(1).withEmailType(EmailType.CREATED_ACCOUNT).withFirstName("a").withId(1).withLastName("").build();
        MandrillEmailStatus feedback = sendEmailService.sendNonAsyncEmailTo(sendTo);
        assertThat(feedback).isEqualTo((MandrillEmailStatus.SENT));
    }


    @Test(expected = SendEmailException.class)
    public void sendNonAsyncEmailTo__sentExpectedButReturnedRejected__exceptionThrown() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = mock(MandrillMessageStatus.class);
        when(mandrillMessageStatus.getStatus()).thenReturn(MandrillEmailStatus.REJECTED.getStatus());

        when(mandrillMessagesApi.sendTemplate(anyString(), anyMapOf(String.class, String.class), anyObject(), eq(Boolean.FALSE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        SendTo sendTo = new SendToBuilder().withEmail("a@b.c").withEmailToken("abcdef").withEmailId(1).withEmailType(EmailType.CREATED_ACCOUNT).withFirstName("a").withId(1).withLastName("").build();
        MandrillEmailStatus feedback = sendEmailService.sendNonAsyncEmailTo(sendTo);
        assertThat(feedback).isEqualTo((MandrillEmailStatus.SENT));
    }

    @Test
    public void sendNonAsyncFeedbackEmailTo__sentExpected__isOk() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = mock(MandrillMessageStatus.class);
        when(mandrillMessageStatus.getStatus()).thenReturn(MandrillEmailStatus.SENT.getStatus());

        when(mandrillMessagesApi.send(anyObject(), eq(Boolean.FALSE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        SendFeedbackTo sendTo = new SendFeedbackToBuilder().withEmail("a@b.c").withEmailId(1).withEmailType(EmailType.FEEDBACK_MESSAGE).withFirstName("a").withId(1).withLastName("").withSubject("subject").withMessage("message").build();
        MandrillEmailStatus feedback = sendEmailService.sendNonAsyncFeedbackEmailTo(sendTo);
        assertThat(feedback).isEqualTo((MandrillEmailStatus.SENT));
    }

    @Test
    public void sendNonAsyncContactEmail__sentExpected__isOk() throws Exception {
        MandrillMessagesApi mandrillMessagesApi = mock(MandrillMessagesApi.class);

        MandrillMessageStatus mandrillMessageStatus = mock(MandrillMessageStatus.class);
        when(mandrillMessageStatus.getStatus()).thenReturn(MandrillEmailStatus.SENT.getStatus());

        when(mandrillMessagesApi.send(anyObject(), eq(Boolean.FALSE))).thenReturn(new MandrillMessageStatus[]{mandrillMessageStatus});

        //-----------------------------------------------------------------
        // Prepare mocks
        //-----------------------------------------------------------------
        when(mandrillService.getApi()).thenReturn(mandrillMessagesApi);

        ContactDTO contactDTO = new ContactDTOBuilder().withEmail("a@b.c").withName("a").withMessage("message").build();
        MandrillEmailStatus feedback = sendEmailService.sendNonAsyncContactEmail(contactDTO);
        assertThat(feedback).isEqualTo((MandrillEmailStatus.SENT));
    }
}