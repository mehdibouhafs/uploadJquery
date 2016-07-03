$ ->
  $.get "/clients", (clients) ->
    $.each clients, (index,client) ->
      $('#clients').append $("<li>").text 'id='+ client.id+ 'name='+client.name

