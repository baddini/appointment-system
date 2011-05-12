using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using System.Text.RegularExpressions;

namespace Calender_System
{
    public partial class WeekView : Form
    {
        private String userID;
        private String userType;
        
        public WeekView(String credentials)
        {
            InitializeComponent();
            //String.split does not work so well when you want to split on a group of characters
            String[] data = Regex.Split(credentials, "=>");
            userID = data[1];
            userType = data[0];
            this.Show();
            this.Focus();
        }

        private void WeekView_Load(object sender, EventArgs e)
        {
            //C# just needs this method for windows loaded after the first... Won't compile without it
        }
    }
}
