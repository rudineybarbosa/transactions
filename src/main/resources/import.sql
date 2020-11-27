insert into account (id, document_number) values (1, '12345678900');

insert into operation_type (description) values ('COMPRA A VISTA');
insert into operation_type (description) values ('COMPRA PARCELADA');
insert into operation_type (description) values ('SAQUE');
insert into operation_type (description) values ('PAGAMENTO');

insert into transaction (amount, event_date, account_id, operation_id) values (-55.56, now(), 1, 1);