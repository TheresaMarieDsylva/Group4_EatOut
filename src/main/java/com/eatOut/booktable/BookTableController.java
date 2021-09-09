package com.eatOut.booktable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BookTableController {
    @RequestMapping(value="/book_table", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewBookTablePage(HttpServletRequest request, Model model)  {
        IValidateBooking validateBooking = new ValidateBooking();
        BookTable bookTable = new BookTable();
        String displayPage = null;
        HttpSession session = request.getSession();
        ValidateBooking.BookTableFlag bookTableFlag = null;
        int peopleCount = 0;
        String peopleCountString = request.getParameter("people_count");
        String bookDate = request.getParameter("b_date");
        String bookTime = request.getParameter("b_time");
        String tableArea = request.getParameter("t_area");
        String specialRequests = request.getParameter("s_requests");
        String restaurantID = (String) session.getAttribute("resId");
        int cusID = (int) session.getAttribute("cusId");

        bookTable=bookTable.showTable(restaurantID);
        model.addAttribute("tableDetails", bookTable);
        if(request.getParameter("timeAvailable")==null){
            displayPage = "book_table";
        }
        else{
            peopleCount = Integer.parseInt(peopleCountString);
            bookTableFlag = validateBooking.checkIfDateIsValid(bookDate);
            if(bookTableFlag.equals(ValidateBooking.BookTableFlag.DATE_NOT_VALID)){
                model.addAttribute("bookingResponse", "Not a valid date, past date chosen");
                displayPage = "book_table";
            }
            else{
                bookTable = bookTable.timeSeatValidity(tableArea, restaurantID, bookDate, bookTime);
                model.addAttribute("tableDetails", bookTable);
                model.addAttribute("people_count", peopleCountString);
                model.addAttribute("b_date", bookDate);
                model.addAttribute("b_time", bookTime);
            }
        }
        if(request.getParameter("bookTableForm")==null ){
            displayPage = "book_table";
        }
        else{
            peopleCount = Integer.parseInt(peopleCountString);
            bookTableFlag = validateBooking.checkIfPeopleCountValid(peopleCount);
            ValidateBooking.BookTableFlag bookTableFlag1 = validateBooking.checkIfDateIsValid(bookDate);
            if(bookTableFlag.equals(ValidateBooking.BookTableFlag.PEOPLE_COUNT_NOT_VALID)){
                model.addAttribute("bookingResponse", "People should be 1 or more");
                displayPage = "book_table";
            }

            else if(bookTableFlag1.equals(ValidateBooking.BookTableFlag.DATE_NOT_VALID)){
                model.addAttribute("bookingResponse", "Not a valid date, past date chosen");
                displayPage = "book_table";
            }
            else{
                bookTable = bookTable.timeSeatValidity(tableArea, restaurantID, bookDate, bookTime);

                if(tableArea.matches("common_seats") && bookTable.getCommonSeatCount().matches("0")){
                    model.addAttribute("bookingResponse", "Selected Table is empty, choose other type...");
                    displayPage = "book_table";
                }
                else if(tableArea.matches("terrace_seats") && bookTable.getTerraceSeatCount().matches("0")){
                    model.addAttribute("bookingResponse", "Selected Table is empty, choose other type...");
                    displayPage = "book_table";
                }
                else if(tableArea.matches("window_seats") && bookTable.getWindowSeatCount().matches("0")){
                    model.addAttribute("bookingResponse", "Selected Table is empty, choose other type...");
                    displayPage = "book_table";
                }
                else if(tableArea.matches("lounge_seats") && bookTable.getLoungeSeatCount().matches("0")){
                    model.addAttribute("bookingResponse", "Selected Table is empty, choose other type...");
                    displayPage = "book_table";
                }
                else if(tableArea.matches("private_seats") && bookTable.getPrivateSeatCount().matches("0")){
                    model.addAttribute("bookingResponse", "Selected Table is empty, choose other type...");
                    displayPage = "book_table";
                }
                else{
                    Boolean value = bookTable.bookTableDB(cusID,peopleCount, bookDate, bookTime, tableArea, specialRequests,restaurantID);
                    if(value){
                        displayPage = "payment";
                    }
                    else{
                        model.addAttribute("bookingResponse", "Something went wrong!");
                        displayPage = "book_table";
                    }
                }
            }
        }

        return displayPage;
    }

    @RequestMapping(value = "/update-seat-values", method = {RequestMethod.POST, RequestMethod.GET})
    public String updateTableValuesPage(HttpServletRequest request,  Model model)  {
        BookTable bookTable = new BookTable();
        HttpSession session = request.getSession();
        String displayPage = null;
        int restaurantID = (int) session.getAttribute("resId");
        String cSeat = request.getParameter("c_seat");
        String cSeatPrice = request.getParameter("c_seat_price");
        String tSeat = request.getParameter("t_seat");
        String tSeatPrice = request.getParameter("t_seat_price");
        String wSeat = request.getParameter("w_seat");
        String wSeatPrice = request.getParameter("w_seat_price");
        String lSeat = request.getParameter("l_seat");
        String lSeatPrice = request.getParameter("l_seat_price");
        String pSeat = request.getParameter("p_seat");
        String pSeatPrice = request.getParameter("p_seat_price");
        if (request.getParameter("updateSeatValues") == null) {
             displayPage = "update-seat-values";
        } else {
            bookTable.updateTableDetails(cSeat,tSeat,wSeat,lSeat,pSeat,cSeatPrice,tSeatPrice,wSeatPrice,lSeatPrice,pSeatPrice, restaurantID);
            model.addAttribute("updateResponse", "Information updated Successfully");
        }

        return displayPage;
    }


}