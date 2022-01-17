<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\ClientServiceDataTable;
use App\Http\Controllers\Controller;

class ClientServiceController extends Controller
{
    public function index(ClientServiceDataTable $dataTable)
    {
        return $dataTable->render('web.admin.settings.clientService.index');
    }
}
