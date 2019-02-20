import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaTypeSdmSuffix, defaultValue } from 'app/shared/model/para-type-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARATYPES: 'paraType/SEARCH_PARATYPES',
  FETCH_PARATYPE_LIST: 'paraType/FETCH_PARATYPE_LIST',
  FETCH_PARATYPE: 'paraType/FETCH_PARATYPE',
  CREATE_PARATYPE: 'paraType/CREATE_PARATYPE',
  UPDATE_PARATYPE: 'paraType/UPDATE_PARATYPE',
  DELETE_PARATYPE: 'paraType/DELETE_PARATYPE',
  SET_BLOB: 'paraType/SET_BLOB',
  RESET: 'paraType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaTypeSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaTypeSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaTypeSdmSuffixState = initialState, action): ParaTypeSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARATYPES):
    case REQUEST(ACTION_TYPES.FETCH_PARATYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARATYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARATYPE):
    case REQUEST(ACTION_TYPES.UPDATE_PARATYPE):
    case REQUEST(ACTION_TYPES.DELETE_PARATYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARATYPES):
    case FAILURE(ACTION_TYPES.FETCH_PARATYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARATYPE):
    case FAILURE(ACTION_TYPES.CREATE_PARATYPE):
    case FAILURE(ACTION_TYPES.UPDATE_PARATYPE):
    case FAILURE(ACTION_TYPES.DELETE_PARATYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARATYPES):
    case SUCCESS(ACTION_TYPES.FETCH_PARATYPE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARATYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARATYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_PARATYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARATYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-types';
const apiSearchUrl = 'api/_search/para-types';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaTypeSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARATYPES,
  payload: axios.get<IParaTypeSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaTypeSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARATYPE_LIST,
    payload: axios.get<IParaTypeSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaTypeSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARATYPE,
    payload: axios.get<IParaTypeSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaTypeSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARATYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaTypeSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARATYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaTypeSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARATYPE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
