using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using System.Net;
using System.IO;
using System.Xml;

namespace Calender_System
{
    public partial class LoginView : Form
    {
        public LoginView()
        {
            InitializeComponent();
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            String responseString = Program.ServiceMethod("GET", Program.baseURI_AS + "Login?username=" + txtName.Text + "&password=" + txtPass.Text);
            //ServiceMethod returns 'ERROR' on critical failure (service being down)
            if (responseString.Equals("ERROR"))
            {
                lblError.Text = "Service is down.";
            }
            //Service returns an empty string if credentials are wrong
            else if (responseString.Equals(""))
            {
                lblError.Text = "Invalid credentials.";
            }
            //Valid credentials, store responce, pass on over to the menu...
            else
            {
                lblError.Text = responseString;//PLACEHOLDER*********************************************************
            }
        }
    }
}
