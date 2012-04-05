package org.qsse.server.handler;

import org.qsse.client.actions.AuthenticateLogin;
import org.qsse.client.actions.CreateAnswer;
import org.qsse.client.actions.CreateArticle;
import org.qsse.client.actions.CreateEvent;
import org.qsse.client.actions.CreateQuestion;
import org.qsse.client.actions.CreateUser;
import org.qsse.client.actions.DeleteArticle;
import org.qsse.client.actions.DeleteUser;
import org.qsse.client.actions.EditProfile;
import org.qsse.client.actions.EditQuestion;
import org.qsse.client.actions.GetAnswers;
import org.qsse.client.actions.GetArticle;
import org.qsse.client.actions.GetEvent;
import org.qsse.client.actions.GetQuestions;
import org.qsse.client.actions.GetSuraList;
import org.qsse.client.actions.GetUploadUrl;
import org.qsse.client.actions.GetUserProfile;
import org.qsse.client.actions.GetUsers;
import org.qsse.client.actions.ReadQuran;
import org.qsse.client.actions.ResetPassword;
import org.qsse.client.actions.Search;
import org.qsse.client.actions.TranslateAhmedRaza;
import org.qsse.client.actions.UserLogout;
import org.qsse.client.actions.GetUserStatus;
import org.qsse.client.actions.DeleteEvent;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

public class ApplicationHandlers extends ActionHandlerModule {

	protected void configureHandlers() {
		// add handler bindding here.

		bindHandler(GetUsers.class, GetUsersHandler.class);
		bindHandler(GetUserProfile.class, GetUserProfileHandler.class);
		bindHandler(CreateUser.class, CreateUserHandler.class);
		bindHandler(EditProfile.class, EditProfileHandler.class);
		bindHandler(AuthenticateLogin.class, AuthenticateLoginHandler.class);
		bindHandler(UserLogout.class, UserLogoutHandler.class);
		bindHandler(GetUserStatus.class, GetUserStatusHandler.class);
		bindHandler(ReadQuran.class, ReadQuranHandler.class);
		bindHandler(GetSuraList.class, GetSuraListHandler.class);
		bindHandler(Search.class, SearchHandler.class);
		bindHandler(TranslateAhmedRaza.class, TranslateAhmedRazaHandler.class);
		bindHandler(GetUploadUrl.class, GetUploadUrlHandler.class);
		bindHandler(ResetPassword.class, ResetPasswordHandler.class);
		bindHandler(GetUserProfile.class, GetSpecificUserProfile.class);
		bindHandler(DeleteUser.class, DeleteUserHandler.class);
		bindHandler(CreateQuestion.class, CreateQuestionHandler.class);
		bindHandler(GetQuestions.class, GetQuestionsHandler.class);
		bindHandler(GetAnswers.class, GetAnswerHandler.class);
		bindHandler(CreateAnswer.class, CreateAnswerHandler.class);
		bindHandler(EditQuestion.class, EditQuestionHandler.class);
		bindHandler(CreateArticle.class, CreateArticleHandler.class);
		bindHandler(GetArticle.class, GetArticleHandler.class);
		bindHandler(DeleteArticle.class, DeleteArticleHandler.class);
		bindHandler(CreateEvent.class, CreateEventHandler.class);
		bindHandler(GetEvent.class, GetEventHandler.class);
		bindHandler(DeleteEvent.class, DeleteEventHandler.class);
	}

}