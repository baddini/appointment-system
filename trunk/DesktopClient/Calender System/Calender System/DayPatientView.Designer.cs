namespace Calender_System
{
    partial class DayPatientView
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.lblTitle = new System.Windows.Forms.Label();
            this.btnBack = new System.Windows.Forms.Button();
            this.lblApt = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // lblTitle
            // 
            this.lblTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitle.Location = new System.Drawing.Point(50, 0);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(400, 34);
            this.lblTitle.TabIndex = 0;
            this.lblTitle.Text = "Appointments for ";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.lblTitle.Click += new System.EventHandler(this.label1_Click);
            // 
            // btnBack
            // 
            this.btnBack.Location = new System.Drawing.Point(200, 506);
            this.btnBack.Name = "btnBack";
            this.btnBack.Size = new System.Drawing.Size(100, 38);
            this.btnBack.TabIndex = 1;
            this.btnBack.Text = "Return To Menu";
            this.btnBack.UseVisualStyleBackColor = true;
            // 
            // lblApt
            // 
            this.lblApt.Location = new System.Drawing.Point(20, 62);
            this.lblApt.Name = "lblApt";
            this.lblApt.Size = new System.Drawing.Size(450, 420);
            this.lblApt.TabIndex = 2;
            this.lblApt.Text = "Blank";
            // 
            // DayPatientView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(484, 562);
            this.Controls.Add(this.lblApt);
            this.Controls.Add(this.btnBack);
            this.Controls.Add(this.lblTitle);
            this.Name = "DayPatientView";
            this.Text = "Appointment View";
            this.Load += new System.EventHandler(this.DayPatientView_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label lblTitle;
        private System.Windows.Forms.Button btnBack;
        private System.Windows.Forms.Label lblApt;
    }
}

