package br.com.popcomic.Servelet;

import br.com.popcomic.dao.UserDao;
import br.com.popcomic.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class CreateLoginUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("Email");
        String senha = req.getParameter("Senha");


        UserDao userDao = new UserDao();
        User user = userDao.ValidarLogin(email, senha);

            //Validação

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("Logado", user);
            resp.sendRedirect("home.jsp");
        } else {
            // Definir mensagem de erro
            req.setAttribute("errorMessage", "E-mail ou senha inválidos!");

            req.getRequestDispatcher("LoginUser.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redireciona para a página de login se o método GET for chamado
        req.getRequestDispatcher("LoginUser.jsp").forward(req, resp);
    }

}
