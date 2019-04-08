﻿using System.ComponentModel.DataAnnotations;

namespace DelControlWeb.ViewModels.Users
{
    public class ChangePasswordViewModel
    {
        public string UserId { get; set; }

        public string Email { get; set; }

        [Required]
        public string NewPassword { get; set; }
    }
}