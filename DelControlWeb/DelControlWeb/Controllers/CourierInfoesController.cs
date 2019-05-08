﻿using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Http;
using System.Web.Http.Description;
using DelControlWeb.Context;
using DelControlWeb.Models;
using Microsoft.AspNet.Identity.Owin;

namespace DelControlWeb.Controllers
{
    public class CourierInfoesController : ApiController
    {
        private ApplicationContext db = System.Web.HttpContext.Current.GetOwinContext().Get<ApplicationContext>();

        // GET: api/LastCourierLocations
        public IHttpActionResult GetCourierInfo()
        {
            List<CourierInfo> couriers = new List<CourierInfo>();
            couriers.Add(new CourierInfo()
            {
                CourierId = "Иванов И.И.",
                Latitude = 53.90F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Петров П.П.",
                Latitude = 53.90F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Сидоров А.А.",
                Latitude = 53.90F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Пупкин О.П.",
                Latitude = 53.90F,
                Longitude = 27.44F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Моник А.П.",
                Latitude = 53.90F,
                Longitude = 27.57F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Лунев Д.В.",
                Latitude = 53.90F,
                Longitude = 27.26F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Потапов В.В.",
                Latitude = 53.50F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            couriers.Add(new CourierInfo()
            {
                CourierId = "Крутько А.Д.",
                Latitude = 53.93F,
                Longitude = 27.56F,
                Speed = 12.5F,
                Time = DateTime.Now.ToLocalTime()
            });
            return Ok(couriers);
            //return db.CourierInfo;
        }

        // GET: api/LastCourierLocations/5
        [ResponseType(typeof(CourierInfo))]
        public IHttpActionResult GetCourierInfo(string id)
        {
            CourierInfo lastCourierLocation = db.CourierInfoes.First(c => c.CourierId == id);
            if (lastCourierLocation == null)
            {
                return NotFound();
            }
            return Ok(lastCourierLocation);
        }

        // PUT: api/LastCourierLocations/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCourierInfo(CourierInfo lastCourierLocation)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if(!CourierInfoExists(lastCourierLocation.CourierId))
            {
                PostCourierInfo(lastCourierLocation);
            }
            db.Entry(lastCourierLocation).State = EntityState.Modified;
            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CourierInfoExists(lastCourierLocation.CourierId))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }
            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/LastCourierLocations
        [ResponseType(typeof(CourierInfo))]
        public IHttpActionResult PostCourierInfo(CourierInfo lastCourierLocation)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            db.CourierInfoes.Add(lastCourierLocation);
            db.SaveChanges();
            return CreatedAtRoute("DefaultApi", new { id = lastCourierLocation.CourierId }, lastCourierLocation);
        }

        // DELETE: api/LastCourierLocations/5
        [ResponseType(typeof(CourierInfo))]
        public IHttpActionResult DeleteCourierInfo(int id)
        {
            CourierInfo lastCourierLocation = db.CourierInfoes.Find(id);
            if (lastCourierLocation == null)
            {
                return NotFound();
            }
            db.CourierInfoes.Remove(lastCourierLocation);
            db.SaveChanges();
            return Ok(lastCourierLocation);
        }

        private bool CourierInfoExists(string id)
        {
            return db.CourierInfoes.Count(e => e.CourierId == id) > 0;
        }
    }
}